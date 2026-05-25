terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "6.17.0"
    }
    null = {
      source = "hashicorp/null"
      version = "3.2.4"
    }
    postgresql = {
      source = "cyrilgdn/postgresql",
      version = "~> 1.22"
    }
    random = {
      source = "hashicorp/random",
      version = "~> 3.0"
    }
  }
}
// VPC and subnets
data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

resource "aws_security_group" "rds_sg" {
  name = "rds-sg"
  vpc_id = data.aws_vpc.default.id

  ingress {
    from_port = 5432
    to_port = 5432
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

// RDS
resource "aws_db_subnet_group" "main" {
  name = "main-subnet-group"
  subnet_ids = data.aws_subnets.default.ids
}

resource "aws_db_instance" "main" {
  identifier = "song-release-system-db"
  engine = "postgres"
  engine_version = "18.3"
  instance_class = "db.t4g.micro"
  allocated_storage = 20
  storage_type = "gp2"

  db_name = "postgres"
  username = "postgres"
  password = var.db_master_password

  db_subnet_group_name = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  publicly_accessible = true
  skip_final_snapshot = true

  tags = {
    name = "song-release-system-rds"
  }
}

provider "postgresql" {
  host = aws_db_instance.main.address
  port = 5432
  username = "postgres"
  password = var.db_master_password
  sslmode  = "require"
  superuser = false
}

resource "postgresql_database" "service_db" {
  for_each = toset(var.services)
  name = each.key
}

resource "postgresql_role" "service_user" {
  for_each = toset(var.services)
  name = "${each.key}_user"
  password = random_password.db_passwords[each.key].result
  login = true
  skip_drop_role = true
  depends_on = [postgresql_database.service_db]
}

resource "postgresql_grant" "service_grant" {
  for_each = toset(var.services)
  database = each.key
  role = "${each.key}_user"
  object_type = "database"
  privileges = ["ALL"]
  depends_on = [postgresql_role.service_user]
}

resource "postgresql_grant" "service_schema_grant" {
  for_each = toset(var.services)
  database = each.key
  role = "${each.key}_user"
  schema = "public"
  object_type = "schema"
  privileges = ["CREATE", "USAGE"]
  depends_on = [postgresql_grant.service_grant]
}

resource "random_password" "db_passwords" {
  for_each = toset(var.services)
  length = 16
  special = false
}

// S3 bucket
resource "aws_s3_bucket" "music_files_bucket" {
  bucket = "uni-cloud-music-files"
  tags = {
    name = "app-storage"
  }
}

resource "aws_s3_bucket_public_access_block" "app" {
  bucket = aws_s3_bucket.music_files_bucket.id
  block_public_acls = false
  block_public_policy = false
  ignore_public_acls = false
  restrict_public_buckets = false
}
