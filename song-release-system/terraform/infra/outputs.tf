output "rds_hostname" {
  value       = aws_db_instance.main.address
  description = "The public endpoint of your RDS database"
}

output "service_db_passwords" {
  value     = { for k, v in random_password.db_passwords : k => v.result }
  sensitive = true
  description = "Map of microservice names to their generated DB passwords"
}