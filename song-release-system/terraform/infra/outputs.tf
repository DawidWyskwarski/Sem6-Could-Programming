output "rds_endpoint" {
  value = aws_db_instance.main.address
}

resource "aws_ssm_parameter" "db_url" {
  for_each = toset(var.services)
  name     = "/microservices/${each.key}/db_url"
  type     = "SecureString"
  value    = "postgresql://${each.key}_user:${random_password.db_passwords[each.key].result}@${aws_db_instance.main.address}:5432/${each.key}"
}

resource "aws_ssm_parameter" "db_password" {
  for_each = toset(var.services)
  name     = "/microservices/${each.key}/db_password"
  type     = "SecureString"
  value    = random_password.db_passwords[each.key].result
}
