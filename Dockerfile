# Use the smartbear/ready-api-virtserver:latest image as the base image
FROM docker.io/smartbear/ready-api-virtserver:latest

# Set environment variables
ENV VirtServerUser=jame
ENV VirtServerPassword=smith
ENV VirtServerUserRole=admin
ENV DockerUser=VirtServerLicenseUser
ENV ACCEPT_TOU=true

# Expose the necessary ports
EXPOSE 9090
EXPOSE 8000-8100
