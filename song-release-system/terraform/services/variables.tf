variable "aws_account_id" {
  type = string
}

variable "aws_region" {
  type = string
  default = "us-east-1"
}

variable "lab_role_arn" {
  type = string
}

variable "dockerhub_username" {
  type = string
}

variable "services" {
  type = map(number)
}

variable "db_host" {
  type = string
}

variable "rabbitmq_url" {
  type = string
}
