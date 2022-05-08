import { Player } from "./player.model";

export class Game {
    id: string;
    p1Id: string;
    p2Id: string;
    gameStatus: string;
    letters: string[];
    wordsUsed: string[];
    winScore: number;
    winner: string;
}