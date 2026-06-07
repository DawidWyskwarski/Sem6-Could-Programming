variable "aws_region" {
  type        = string
  default     = "us-east-1"
}

variable "project_name" {
  type        = string
  default     = "qr-code-generator"
}

variable "s3_bucket_name" {
  type        = string
}

variable "dynamodb_table_name" {
  type        = string
  default     = "qr-code-metadata"
}

variable "lambda_jar_path" {
  type        = string
  default     = "../build/libs/qr-code-handler.jar"
}

variable "lambda_handler" {
  type        = string
  default     = "QrCodeHandler"
}