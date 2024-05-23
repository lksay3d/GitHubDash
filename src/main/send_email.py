import smtplib
from email.mime.text import MIMEText
import os
import sys

def send_email(to_email, subject, body):
    from_email = os.getenv("EMAIL_USERNAME")
    password = os.getenv("EMAIL_PASSWORD")

    if not from_email or not password:
        raise ValueError("Email credentials are not set in environment variables")

    msg = MIMEText(body)
    msg["Subject"] = subject
    msg["From"] = from_email
    msg["To"] = to_email

    with smtplib.SMTP_SSL("smtp.gmail.com", 465) as server:
        server.login(from_email, password)
        server.sendmail(from_email, to_email, msg.as_string())

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: send_email.py <to_email> <subject> <body>")
        sys.exit(1)

    to_email = sys.argv[1]
    subject = sys.argv[2]
    body = sys.argv[3]

    try:
        send_email(to_email, subject, body)
    except Exception as e:
        print(f"Failed to send email: {e}")
        sys.exit(1)