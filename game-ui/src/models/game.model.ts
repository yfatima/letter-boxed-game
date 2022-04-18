import { Player } from "./player.model";

export class Game {
    id: String;
    P1: Player;
    P2: Player;
    gameStatus: String;
    letters: String[];
    winScore: number;
}