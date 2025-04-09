import { html } from '../lib/preact-htm-standalone.js'

const kinds = { '2': '2', '3': '3', '4': '4', '5': '5', '6': '6', '7': '7', '8': '8', '9': '9', '10': '10', 'jack': 'J', 'queen': 'Q', 'king': 'K', 'ace': 'A', '?' : '?' }
const suits = { 'hearts': '♥', 'diamonds': '♦', 'clubs': '♣', 'spades': '♠', '?' : '?' }

//TODO distinguish between 400 and 500 error
export default function Table(props) {
    const styleFor = (c) => '♥♦'.includes(suits[c.suit]) ? 'color: red;' : 'color: black;'
    const card = (c) => html`<div class=card style=${styleFor(c)}><div>${kinds[c.rank]}</div><div>${suits[c.suit]}</div></div>`

    const cardsFor = (player) => {
        const cards = (player.id === props.me.id) ? props.me.cards : [{ rank: '?', suit: '?' }, { rank: '?', suit: '?' }]

        return player.active ? html`
            <div class=cards>
                ${card(cards[0])}
                ${card(cards[1])}
            </div>
        ` : ''
    }

    const players = props.players.map((player, i) => html`
        <div class=player title='${player.name}\nCash: $${player.cash}' style='--i:${i};'>
            <div class='player-name ${player.id === props.currentPlayer ? "player-active" : ""}'>${player.name}</div>
            ${cardsFor(player)}
            <div class=player-cash>$${player.cash}</div>
        </div>
        <div class=player-bet style='--i:${i};'>
            ${player.bet ? '$' + player.bet : ''}
        </div>
    `)

    const pot = props.pot ? html`<div class=pot>💰 ${props.pot}</div>` : ''
    const communityCards = html`<div class=cards>${props.communityCards.map(c => card(c))}</div>`
    const winner = props.winner ? html`<dialog open>
        <div>Winner: ${props.winner.name}</div>
        <div class=cards>${props.winnerHand.map(c => card(c))}</div>
    </dialog>` : ''

    return html`
        <div class=table>
            ${winner}
            ${players}
            <div class=center>
                ${pot}
                ${communityCards}
            </div>
        </div>
    `
}
