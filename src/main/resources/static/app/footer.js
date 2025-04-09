import { html, useState, useRef } from '../lib/preact-htm-standalone.js'

export default function Footer(props) {
    const [amount, setAmount] = useState(10)
    const raiseButton = useRef()

    const handleInput = (e) => {
        setAmount(e.target.value)
        raiseButton.current.disabled = !e.target.value.trim()        
    }

    const handleKeyPress = (e) => {
        if ((e.key === 'Enter') && (amount > 0)) {
            props.onAction({ type: 'raise', amount })
        }
    }

    const actions = html`
        <div>
            <button disabled=${!props.canBet} onClick=${() => props.onAction({ type: 'check', args: [amount] })}>Check</button>
            <button disabled=${!props.canBet} ref=${raiseButton} onClick=${() => props.onAction({ type: 'raise', args: [amount] })}>Raise To</button>
            <input disabled=${!props.canBet} type=number placeholder='Enter Amount' value=${amount} onInput=${handleInput} onKeyPress=${handleKeyPress}></input>
            <button disabled=${!props.canBet} onClick=${() => props.onAction({ type: 'call', args: [amount] })}>Call</button>
            <button disabled=${!props.canBet} onClick=${() => props.onAction({ type: 'fold', args: [amount] })}>Fold</button>
        </div>
    `

    return html`
        <footer>${props.joined ? actions : 'Not joined'}</footer>
    `
}
