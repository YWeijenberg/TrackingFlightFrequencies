package com.TrackingFlightFrequencies.Variables

import com.databricks.dbutils_v1.DBUtilsHolder.dbutils

trait GlobalVars {
  val url = "http://api.aviationstack.com/v1/flights?dep_icao=EHAM"
  private val secretRgName = "rgName"
  private val secretScope = "keyvault-managed"
  private val secretKey = "apiKey"
  private val secretSas = "storageSas"
  private val storageAccountnameKey = "storageAccountName"
  private val containerNameKey = "containerName"
  val rg_name: String = dbutils.secrets.get(secretScope, secretRgName)
  val apiKey: String = dbutils.secrets.get(secretScope, secretKey)
  val sasToken: String = dbutils.secrets.get(secretScope, secretSas)
  val storageAccountname: String = dbutils.secrets.get(secretScope, storageAccountnameKey)
  val containerName: String = dbutils.secrets.get(secretScope, containerNameKey)
}
