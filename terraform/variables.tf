variable "region" {
  type        = string
  default     = "West Europe"
  description = "The Azure region where the project will be deployed."
}

variable "rg_name" {
  type        = string
  default     = "TrackingFlightFrequencies"
  description = "Name of resource group where project will be deployed."
}

variable "prefix" {
  type        = string
  default     = "FlightFreq"
  description = "Default prefix for all resources excluding rg_name."
}

variable "keyvault_secrets" {
  type        = map(string)
  description = "Map of secrets that should be declared in the key vault"
}

variable "subscriptionId" {
  type        = string
  description = "The id of the used subscription"
}

variable "EntraIDUsername" {
  type        = string
  description = "Entra ID username of the user logged into CLI"
}