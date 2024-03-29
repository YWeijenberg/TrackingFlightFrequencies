package com.TrackingFlightFrequencies.Ingestion.FlightData

import java.time.{LocalDateTime, ZoneId, LocalDate}
import java.time.format.DateTimeFormatter

object ApiRequest {
  def request(url: String, apiKey: String, apiHost: String): String = {

    // Set timezone, date format, and today's date
    val zoneId = ZoneId.of("Europe/Amsterdam")
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val today = LocalDate.now(zoneId)

    // Define start and end times for the two API requests
    val morningStart = LocalDateTime.of(today, java.time.LocalTime.MIDNIGHT)
    val morningEnd = LocalDateTime.of(today, java.time.LocalTime.NOON.minusSeconds(1))
    val eveningStart = LocalDateTime.of(today, java.time.LocalTime.NOON)
    val eveningEnd = LocalDateTime.of(today, java.time.LocalTime.MAX).minusSeconds(1)

    // Format periods to strings
    val morningStartStr = morningStart.format(dateFormatter)
    val morningEndStr = morningEnd.format(dateFormatter)
    val eveningStartStr = eveningStart.format(dateFormatter)
    val eveningEndStr = eveningEnd.format(dateFormatter)

    // First request for the morning period
    val morningResponse = requests.get(
      url = url,
      headers = Map(
        "X-RapidAPI-Key" -> apiKey,
        "X-RapidAPI-Host" -> apiHost
      ),
      params = Map(
        "fromLocal" -> morningStartStr,
        "toLocal" -> morningEndStr,
        "withPrivate" -> "false"
      )
    )

    // Second request for the evening period
    val eveningResponse = requests.get(
      url = url,
      headers = Map(
        "X-RapidAPI-Key" -> apiKey,
        "X-RapidAPI-Host" -> apiHost
      ),
      params = Map(
        "fromLocal" -> eveningStartStr,
        "toLocal" -> eveningEndStr,
        "withPrivate" -> "false"
      )
    )

    // Concatenate responses
    val combinedResponse = (morningResponse, eveningResponse) match {
      case (mr, er) if mr.statusCode == 200 && er.statusCode == 200 => mr.text + er.text
      case (mr, _) if mr.statusCode != 200 => s"Morning Error: ${mr.statusCode}"
      case (_, er) if er.statusCode != 200 => s"Evening Error: ${er.statusCode}"
    }

    combinedResponse
  }
}
