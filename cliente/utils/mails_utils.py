from decouple import config
import smtplib
from email_validator import validate_email, EmailNotValidError
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

class MailSendError(Exception):
    """Excepción personalizada para errores de envío de correo"""
    def __init__(self, message, code=None):
        super().__init__(message)
        self.code = code

def validarEmail(email: str) -> str:
    """
    Valida que el email tenga un formato correcto.
    """
    try:
        validate_email(email)
        return email
    except EmailNotValidError as e:
        raise MailSendError(f"Correo inválido: {e}", code=400)

def enviarPasswordPorEmail(email_destino, usuario_nombre, password):
    """
    Envía un correo de bienvenida al usuario con su contraseña generada.
    """
    # Configuración del servidor smtp para el envio de mails
    remitente = config('SENDER', cast=str) #Nuesta cuenta gmail del sistema
    smtp_server = config('SMTP_SERVER', cast=str) #Servidor SMTP
    smtp_port = config('SMTP_PORT', cast=int) #Puerto para el servidor SMTP
    password_smtp = config("SMTP_PASSWORD", cast=str) # Contraseña de aplicación generada

    # Creacion del mensaje
    msg = MIMEMultipart()
    msg['From'] = f"EmpujeComunitarioApp <{remitente}>"
    msg['To'] = email_destino
    msg['Subject'] = 'Bienvenido a EmpujeComunitarioApp - Aqui esta tu contraseña!'

    # Contenido HTML del correo
    cuerpo = f"""
    <html>
        <body style="font-family: Arial, sans-serif; color: #333;">
            <div style="max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;">
                <h2 style="color: #2E86C1;">¡Bienvenido a EmpujeComunitarioApp!</h2>
                <p>Hola <b>{usuario_nombre}</b>,</p>
                <p>Tu usuario ha sido creado correctamente en <b>EmpujeComunitarioApp</b>.</p>
                <p><b>Tu contraseña generada es:</b> <span style="background-color: #f0f0f0; padding: 5px 10px; border-radius: 5px;">{password}</span></p>
                <br>
                <p>Saludos,<br>Equipo de <b>EmpujeComunitarioApp</b></p>
            </div>
        </body>
    </html>
    """
    msg.attach(MIMEText(cuerpo, 'html'))

    # Envío mediante SMTP
    try:
        with smtplib.SMTP(smtp_server, smtp_port) as server:
            server.starttls()
            server.login(remitente, password_smtp)
            server.send_message(msg)
            print(f"Correo enviado correctamente a {email_destino}")
    except smtplib.SMTPRecipientsRefused:
        raise MailSendError(f"El correo electrónico '{email_destino}' es inválido.", code=400)
    except Exception as e:
        raise MailSendError(f"No se pudo enviar el correo a '{email_destino}': {e}", code=500)