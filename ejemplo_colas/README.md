# Demo Simple: Colas de Mensajería

Versión simplificada para fines académicos. Demuestra el concepto fundamental de **Productor → Cola → Consumidor**.

## 🎯 Objetivo Educativo

Enseñar el concepto básico de colas de mensajería con un ejemplo simple y claro.

## 🏗️ Arquitectura Simple

```
📱 API REST → 📤 Productor → 📦 Cola → 👥 Consumidor
     ↓              ↓         ↓        ↓
  POST /api     Envía    RabbitMQ  Procesa
  /enviar      mensaje   almacena  mensaje
```

## 🚀 Ejecutar el Proyecto

### Requisitos
- Java 17+
- Maven 3.6+
- RabbitMQ

### Instalar y Ejecutar

#### **Para macOS/Linux:**
```bash
# 1. Instalar RabbitMQ
brew install rabbitmq

# 2. Iniciar RabbitMQ
brew services start rabbitmq
```

#### **Para Windows:**
```bash
# 1. Descargar e instalar RabbitMQ
# Descargar desde: https://www.rabbitmq.com/download.html
# Instalar Erlang primero (requisito): https://erlang.org/download/

# 2. Instalar RabbitMQ como servicio
# Ejecutar como Administrador:
"C:\Program Files\RabbitMQ Server\rabbitmq_server-3.12.0\sbin\rabbitmq-service.bat" install

# 3. Iniciar el servicio
"C:\Program Files\RabbitMQ Server\rabbitmq_server-3.12.0\sbin\rabbitmq-service.bat" start

# 4. Habilitar plugin de gestión (opcional pero recomendado)
"C:\Program Files\RabbitMQ Server\rabbitmq_server-3.12.0\sbin\rabbitmq-plugins.bat" enable rabbitmq_management
```

#### **Para ambos sistemas:**
```bash
# 4. Ejecutar aplicación
mvn spring-boot:run
```

API disponible en: http://localhost:8081

**Nota**: Para Windows, la RabbitMQ Management UI estará disponible en: http://localhost:15672 (usuario: guest, contraseña: guest)

## 📡 Endpoints Simples

### POST /api/enviar
Envía un mensaje a la cola

```bash
curl -X POST http://localhost:8081/api/enviar \
  -H "Content-Type: application/json" \
  -d '{"contenido": "Hola mundo desde la cola"}'
```

**Respuesta:**
```json
{
  "message": "Mensaje enviado a la cola correctamente",
  "contenido": "Hola mundo desde la cola",
  "estado": "EN_COLA"
}
```

### GET /api/info
Información del sistema

```bash
curl http://localhost:8081/api/info
```

### POST /api/demo
Demostración con 3 mensajes

```bash
curl -X POST http://localhost:8081/api/demo
```

## 🔍 Flujo de Mensajes

1. **Cliente** hace POST a `/api/enviar`
2. **API** responde inmediatamente "Mensaje enviado"
3. **Productor** envía mensaje a RabbitMQ
4. **Cola** almacena el mensaje
5. **Consumidor** procesa el mensaje (2 segundos)

## 📊 Logs de Ejemplo

```
📤 PRODUCTOR: Mensaje enviado a la cola -> Hola mundo desde la cola
==================================================
📥 CONSUMIDOR: Mensaje recibido de la cola
🆔 ID: abc-123
📝 Contenido: Hola mundo desde la cola
🎯 Tipo: MENSAJE
📍 Origen: API_CLIENT
⏰ Timestamp: 2024-03-12T14:30:00
==================================================
⏳ CONSUMIDOR: Procesando mensaje... (2 segundos)
✅ CONSUMIDOR: Mensaje procesado exitosamente
🎉 RESULTADO: 'Hola mundo desde la cola' ha sido procesado
```

## 🎓 Conceptos Clave

### ✅ **Desacoplamiento**
- API no espera al consumidor
- Productor y consumidor son independientes

### ✅ **Asíncrono**
- Respuesta inmediata de la API
- Procesamiento en background

### ✅ **Persistencia**
- Mensajes guardados en RabbitMQ
- Si el consumidor falla, el mensaje espera

### ✅ **Escalabilidad**
- Múltiples consumidores pueden procesar
- Fácil agregar más capacidad

## 🧪 Ejemplo para Clases

1. **Enviar mensaje simple:**
```bash
curl -X POST http://localhost:8081/api/enviar \
  -H "Content-Type: application/json" \
  -d '{"contenido": "Mensaje de prueba para la clase"}'
```

2. **Ver los logs** del consumidor procesando

3. **Enviar múltiples mensajes** para ver la cola en acción

4. **Ver RabbitMQ UI** en http://localhost:15672

## 📱 Postman Collection

Importa `postman-collection-simple.json` para tener los endpoints listos:

1. **Info** - Verificar sistema
2. **Enviar Mensaje** - Probar cola
3. **Demo** - 3 mensajes automáticos

## 🔧 Configuración Mínima

- **1 Cola**: `mensajes.simple`
- **1 Exchange**: `exchange.simple`
- **1 Routing Key**: `routing.key.simple`

## 📈 Beneficios Académicos

- ✅ **Concepto claro**: Sin complejidad innecesaria
- ✅ **Logs detallados**: Fácil de seguir el flujo
- ✅ **Respuesta rápida**: Los estudiantes ven resultados inmediatos
- ✅ **Visual**: RabbitMQ UI muestra la cola funcionando
- ✅ **Extensible**: Base para conceptos más avanzados

Este ejemplo es perfecto para introducir colas de mensajería en clases de arquitectura de software o sistemas distribuidos.
