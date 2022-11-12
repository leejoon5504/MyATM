class MyATM {
  private static MyATM myATM = null;

  private MyATM() { }

  Card card;

  public static MyATM getMyATM() {
    if (myATM == null) {
      myATM = new MyATM();
    }
    return myATM;
  }

  public void insertCard(Card card) {
    this.card = card;
  }

  public boolean validatePin(String pin) {
    if (Server.validatePin(card, pin)) { // Send PIN to the server and validate
      return true;
    } else {
      this.card = null;
      return false;
    }
  }

  public Account selectAccount() {
  }
}
