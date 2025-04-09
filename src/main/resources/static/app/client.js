export default function Client(fetch, EventSource) {
    const baseUrl = '/api/v1'

    this.join = (id, name) => post(`${baseUrl}/players`, { id, name })

    this.performAction = (id, action) => post(`${baseUrl}/actions`, action)
    
    this.start = () => post(`${baseUrl}/start`, '')

    this.onError = (callback) => {
        this.errorCallback = callback
    }

    this.connect = (id, onMessage, onConnect, onError) => {
        const eventSource = new EventSource(`${baseUrl}/events/${id}`)
        eventSource.onmessage = (event) => onMessage(JSON.parse(event.data))
        eventSource.onopen = () => onConnect()
        eventSource.onerror = () => onError()
    }

    const post = async (path, body) => {
        const response = await fetch(path, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        })
        await assertNoError(response)
    }

    const assertNoError = async (response) => {
        if (response.status !== 204) {
            const error = await response.json()
            this.errorCallback(error)
            throw new Error(JSON.stringify(error))
        }
    }
}
