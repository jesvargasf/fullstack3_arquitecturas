# Demo: Arquitectura Basada en Eventos - Procesamiento Asíncrono

Este proyecto demuestra una arquitectura basada en eventos con Spring Boot y colas de mensajería para procesamiento asíncrono de tareas.

## 🎯 Objetivo Educativo

Mostrar cómo una API REST puede responder inmediatamente (`200 OK`) mientras el procesamiento real ocurre en background a través de una cola de eventos.

## 🏗️ Arquitectura

```
Cliente React → POST /api/tareas → Spring Boot API → 200 OK (inmediato)
                                    ↓
                              Publica evento
                                    ↓
                              Cola RabbitMQ
                                    ↓
                          Consumidor procesa (5-10s)
                                    ↓
                              Actualiza estado
```

## 🚀 Ejecutar el Proyecto

### Requisitos
- Java 17+
- Maven 3.6+

### Ejecutar Spring Boot
```bash
mvn spring-boot:run
```

La API estará disponible en: http://localhost:8080

**Nota**: Este proyecto usa procesamiento asíncrono en memoria, no requiere RabbitMQ instalado. Para usar RabbitMQ, descomenta las dependencias AMQP en pom.xml.

## 📡 Endpoints

### POST /api/tareas
Crea una nueva tarea y la envía a la cola para procesamiento asíncrono.

**Request:**
```json
{
  "descripcion": "Procesar archivo CSV"
}
```

**Response (inmediato):**
```json
{
  "message": "Tarea recibida y en procesamiento",
  "tareaId": "uuid-123",
  "estado": "PENDIENTE"
}
```

### GET /api/tareas/{id}
Consulta el estado de una tarea específica.

**Response:**
```json
{
  "id": "uuid-123",
  "descripcion": "Procesar archivo CSV",
  "estado": "COMPLETADA",
  "fechaCreacion": "2024-03-12T14:30:00",
  "fechaProcesamiento": "2024-03-12T14:30:05",
  "resultado": "Tarea procesada exitosamente en 7234ms"
}
```

### GET /api/tareas
Lista todas las tareas creadas.

## 🔍 Flujo de Eventos

1. **Cliente envía POST** → API responde inmediatamente
2. **Productor publica** evento en cola RabbitMQ
3. **Consumidor recibe** evento y procesa en background
4. **Estado actualizado** durante el procesamiento
5. **Cliente consulta** estado periódicamente

## 📊 Estados de Tarea

- **PENDIENTE**: Recibida, en cola para procesar
- **PROCESANDO**: Siendo procesada actualmente
- **COMPLETADA**: Procesamiento exitoso
- **ERROR**: Falló durante el procesamiento

## 🧪 Ejemplos de Uso

```bash
# Crear tarea
curl -X POST http://localhost:8080/api/tareas \
  -H "Content-Type: application/json" \
  -d '{"descripcion": "Generar reporte mensual"}'

# Consultar estado
curl http://localhost:8080/api/tareas/{id}

# Listar todas
curl http://localhost:8080/api/tareas
```

## 📚 Conceptos Demostrados

- ✅ **Desacoplamiento**: API y procesamiento separados
- ✅ **Resiliencia**: La cola persiste si el consumidor falla
- ✅ **Escalabilidad**: Múltiples consumidores pueden procesar
- ✅ **Respuesta inmediata**: UX mejorado con feedback rápido
- ✅ **Procesamiento asíncrono**: Tareas largas no bloquean API

## 🔧 Configuración

En `application.yml` puedes ajustar:
- Puerto del servidor
- Configuración de RabbitMQ
- Niveles de logging

## 📝 Logs de Ejemplo

```
🚀 Tarea creada y enviada a cola: abc-123
✅ API POST respondió inmediatamente con OK para tarea: abc-123
🎧 Consumidor recibió tarea: abc-123 - Generar reporte mensual
⏳ Procesando tarea abc-123 por 7234ms...
✅ Tarea completada: abc-123
```
