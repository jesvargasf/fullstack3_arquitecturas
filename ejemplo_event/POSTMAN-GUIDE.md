# Guía Postman - API Eventos Asíncronos

## 📥 Importar Colección

1. Abrir Postman
2. File → Import
3. Seleccionar el archivo `postman-collection.json`
4. Listo! Ya tienes todas las APIs configuradas

## 🚀 Flujo de Prueba Recomendado

### 1. **Crear Tarea** (Primera petición)
```
POST http://localhost:8080/api/tareas
Body: {"descripcion": "Procesar archivo CSV de ventas"}
```
✅ **Respuesta inmediata**: 200 OK con ID y estado PENDIENTE

### 2. **Consultar Progreso** (Después de 2-3 segundos)
```
GET http://localhost:8080/api/tareas/{{tareaId}}
```
🔄 **Estado esperado**: PROCESANDO

### 3. **Ver Resultado Final** (Después de 5-10 segundos)
```
GET http://localhost:8080/api/tareas/{{tareaId}}
```
✅ **Estado esperado**: COMPLETADA con resultado

### 4. **Procesamiento Concurrente**
Ejecuta rápidamente "Crear Múltiples Tareas" 3-4 veces y luego consulta todas las tareas.

### 5. **Manejo de Errores**
Usa "Simular Error" y consulta esa tarea para ver el estado ERROR.

## 📊 Variables Automáticas

La colección guarda automáticamente:
- `{{tareaId}}` - ID de la primera tarea creada
- `{{tareaId2}}` - ID de la segunda tarea  
- `{{tareaIdError}}` - ID de la tarea de error
- `{{tareaEstado}}` - Estado actual

## 🔍 Tests Automáticos

Cada petición incluye tests que verifican:
- Status codes correctos
- Estructura de respuestas
- Estados válidos
- Logs en consola con información útil

## 🎯 Conceptos a Demostrar

1. **Respuesta Inmediata**: POST responde en <100ms
2. **Procesamiento Background**: Tarea tarda 5-10s
3. **Concurrencia**: Múltiples tareas en paralelo
4. **Estados Claros**: PENDIENTE → PROCESANDO → COMPLETADA/ERROR
5. **Manejo de Errores**: 10% probabilidad de fallo

## 💡 Tips para Clases

- **Ejecuta POST primero**: Muestra la respuesta rápida
- **Usa la consola**: Los logs muestran timestamps y estados
- **Crea varias tareas**: Demuestra el pool de hilos
- **Simula errores**: Muestra resiliencia del sistema

## 📱 curl Commands (Alternativa)

```bash
# Crear tarea
curl -X POST http://localhost:8080/api/tareas \
  -H "Content-Type: application/json" \
  -d '{"descripcion": "Procesar archivo CSV"}'

# Consultar tarea
curl http://localhost:8080/api/tareas/{ID}

# Listar todas
curl http://localhost:8080/api/tareas
```

## 🔧 Configuración del Servidor

- **URL Base**: http://localhost:8080
- **Puerto**: 8080 (asegurar que no esté ocupado)
- **Headers**: Content-Type: application/json para POST

## 📈 Monitoreo en Tiempo Real

1. Crear tarea
2. Inmediatamente consultar (ver PENDIENTE)
3. Consultar cada 2 segundos (ver PROCESANDO)
4. Esperar 5-10 segundos (ver COMPLETADA)

Esto demuestra perfectamente la separación entre respuesta inmediata y procesamiento asíncrono.
