variable "db_master_password" {
  description = "db master password"
  type        = string
  sensitive   = true
}

variable "services" {
  description = "list of services names"
  type = list(string)
  default = [
    "file_service",
    "notification_service",
    "playlist_service",
    "tag_service",
    "track_service"
  ]
}