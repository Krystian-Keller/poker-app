import { html, useState, useRef, useEffect } from '../lib/preact-htm-standalone.js'

export default function Header(props) {
    const [name, setName] = useState('')
    const joinButton = useRef()
    const inputRef = useRef()

    useEffect(() => {
        inputRef.current.focus()
    }, [])

    const handleKeyPress = (e) => {
        if ((e.key === 'Enter') && (name.trim())) {
            props.onJoin(name.trim())
        }
    }

    const handleInput = (e) => {
        setName(e.target.value)
        joinButton.current.disabled = !e.target.value.trim()
    }

    const startButton = props.canStart ? html`<button onClick=${props.onStart}>Start</button>` : ''
    const disconnected = !props.connected ? html`<span title='Connection lost, will attempt reconnecting'>❗️</span>` : ''
    const joinedPlayer = html`<span style='margin-right: .5em;'>${props.me.name}</span>${startButton}${disconnected}`
    const askToJoin = html`
        <input ref=${inputRef} type=text placeholder=Name value=${name} onInput=${handleInput} onKeyPress=${handleKeyPress} />
        <button ref=${joinButton} onClick=${() => props.onJoin(name.trim())} disabled>Join</button>
    `
    const userInfo = props.joined ? joinedPlayer : askToJoin

    return html`
        <header>
            <div class=logo>♠️♥️ Poker</div>
            <div class=user-info>${userInfo}</div>
        </header>
    `
}
