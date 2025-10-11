import bcrypt
import secrets
import string

def hashPassword(password: str) -> str:
    """Devuelve un hash seguro de la contraseña."""
    salt = bcrypt.gensalt()
    return bcrypt.hashpw(password.encode('utf-8'), salt).decode('utf-8')

def verifyPassword(password: str, hashed: str) -> bool:
    """Verifica que la contraseña coincida con el hash."""
    return bcrypt.checkpw(password.encode('utf-8'), hashed.encode('utf-8'))

def generateRandomPassword(length: int = 12) -> str:
    """Genera una contraseña aleatoria segura con mayúsculas, minúsculas, números y símbolos."""
    length = max(length, 10)

    categories = {
        "lower": string.ascii_lowercase,
        "upper": string.ascii_uppercase,
        "digits": string.digits,
        "symbols": "!@#$%^&*()-_=+[]{};:,.<>?"
    }

    # Garantizar al menos un carácter de cada categoría
    password = [secrets.choice(cat) for cat in categories.values()]

    # Completar con caracteres aleatorios de todas las categorías
    all_chars = "".join(categories.values())
    password += [secrets.choice(all_chars) for _ in range(length - len(password))]

    secrets.SystemRandom().shuffle(password)
    return "".join(password)