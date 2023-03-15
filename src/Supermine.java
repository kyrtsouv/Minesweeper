public class Supermine extends Mine {

    public Supermine(Game game, Mine mine) {
        super(game, mine);
    }

    @Override
    public void rightClick() {
        super.rightClick();
        if (!isRevealed && isFlagged && game.getFlagsForSupermine() >= 0) {
            game.revealAxes(this);
            this.setGraphic(null);
        }
    }

}