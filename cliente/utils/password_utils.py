import bcrypt

def hashPassword(password: str) -> str:
    """Genera un hash seguro para la contraseña"""
    salt = bcrypt.gensalt()
    hashed = bcrypt.hashpw(password.encode('utf-8'), salt)
    return hashed.decode('utf-8')

def verifyPassword(password: str, hashed: str) -> bool:
    """Verifica si la contraseña coincide con el hash"""
    return bcrypt.checkpw(password.encode('utf-8'), hashed.encode('utf-8'))
