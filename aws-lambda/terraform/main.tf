terraform {
  required_version = ">= 1.6.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

# S3 Bucket
resource "aws_s3_bucket" "qr_codes" {
  bucket = var.s3_bucket_name
  force_destroy = true
}

# Access options for S3 bucket
resource "aws_s3_bucket_public_access_block" "qr_codes" {
  bucket = aws_s3_bucket.qr_codes.id

  block_public_acls = false
  block_public_policy = false
  ignore_public_acls = false
  restrict_public_buckets = false
}

# Making files public
resource "aws_s3_bucket_policy" "qr_codes_public_read" {
  bucket = aws_s3_bucket.qr_codes.id

  depends_on = [aws_s3_bucket_public_access_block.qr_codes]

  policy = jsonencode({
    Statement = [
      {
        Sid = "PublicReadGetObject"
        Effect = "Allow"
        Principal = "*"
        Action = "s3:GetObject"
        Resource = "${aws_s3_bucket.qr_codes.arn}/*"
      }
    ]
  })
}

# DynamoDB Table
resource "aws_dynamodb_table" "qr_codes" {
  name = var.dynamodb_table_name
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "url"

  attribute {
    name = "url"
    type = "S"
  }

  ttl {
    attribute_name = "ttl"
    enabled = true
  }
}


# Lambda Function
data "aws_iam_role" "lab_role" {
  name = "LabRole"
}

resource "aws_lambda_function" "qr_code_handler" {
  function_name = "${var.project_name}-handler"
  role = data.aws_iam_role.lab_role.arn
  handler = var.lambda_handler
  runtime = "java21"
  filename = var.lambda_jar_path
  timeout = 30
  memory_size = 512

  source_code_hash = filebase64sha256(var.lambda_jar_path)

  environment {
    variables = {
      S3_BUCKET_NAME = aws_s3_bucket.qr_codes.bucket
      DYNAMODB_TABLE_NAME = aws_dynamodb_table.qr_codes.name
    }
  }
}
