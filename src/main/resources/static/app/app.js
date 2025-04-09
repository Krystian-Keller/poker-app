import { html, useEffect, useState } from '../lib/preact-htm-standalone.js'
import Header from './header.js'
import Footer from './footer.js'
import Table from './table.js'

//REVISE the table should be a state
export default function App(props) {
    const [me, setMe] = useState({ id: '', name: '', cards: [] })
    const [state, setState] = useState(0)
    const [joined, setJoined] = useState(false)
    const [currentPlayer, setCurrentPlayer] = useState('')
    const [players, setPlayers] = useState([])
    const [pot, setPot] = useState(0)
    const [communityCards, setCommunityCards] = useState([])
    const [connected, setConnected] = useState(false)
    const [winner, setWinner] = useState('')
    const [winnerHand, setWinnerHand] = useState([])

    useEffect(() => {
        props.client.onError(handleError)
        if (window.location.hash) {
            const [id, name] = window.location.hash.substring(1).split('/')
            finalizeJoin(id, name)
        }
    }, [])

    const handleError = (error) => {
        alert(error.message || `Unknown error: ${JSON.stringify(error)}`)
    }

    const update = (table) => {
        setState(table.state)
        setCurrentPlayer((table.currentPlayer || { id: '' }).id)
        table.players.forEach(p => p.bet = table.bets[p.id])
        setPlayers(table.players)
        setPot(table.pot)
        setMe((me) => ({ ...me, cards: table.playerCards }))
        setCommunityCards(table.communityCards)
        setWinner(table.winner)
        setWinnerHand(table.winnerHand)
    }

    const generateRandomId = () => {
        return [...Array(8)].map(() => Math.floor(Math.random() * 16).toString(16)).join('');
    }

    const handleJoin = async (name) => {
        const id = generateRandomId()
        await props.client.join(id, name)
        window.location.hash = `${id}/${name}`
        await finalizeJoin(id, name)
    }

    const finalizeJoin = async (id, name) => {
        props.client.connect(id, (msg) => update(msg), () => setConnected(true), () => setConnected(false))
        setMe((me) => ({ ...me, id, name }))
        setJoined(true)
    }

    const handleStart = async () => {
        await props.client.start()
    }

    const handleAction = async (action) => {
        await props.client.performAction(me.id, action)
    }

    const isFirstPlayer = (players[0] || {}).id === me.id
    const isOpenOrEndedState = state === 0 || state === 5
    const hasMoreThanOnePlayer = players.length > 1
    const canStart = isFirstPlayer && isOpenOrEndedState && hasMoreThanOnePlayer
    const canBet = !isOpenOrEndedState && (currentPlayer === me.id)

    return html`<div class=app>
        <${Header} joined=${joined} onJoin=${handleJoin} canStart=${canStart} onStart=${handleStart} me=${me} connected=${connected} />
        <${Table} players=${players} currentPlayer=${currentPlayer} me=${me} state=${state} pot=${pot} communityCards=${communityCards} winner=${winner} winnerHand=${winnerHand} />
        <${Footer} onAction=${handleAction} joined=${joined} canBet=${canBet} />
    </div>
    `
}
