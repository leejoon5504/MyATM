class MyATM {
  private static MyATM myATM = null;
  
  int balance;
  
  private MyATM() { }
  
  public static MyATM getMyATM() {
    if (myATM == null) {
      myATM = new MyATM();
    }
    return myATM;
  }

  Card card;
  Account[] accounts;

  public void insertCard(Card card) throws Exception {
    try {
      Server.readCard(card); // Read card from the server
      this.card = card;
    } catch (Exception e) {
      throw e; // Failed to read the card
    }
  }

  public boolean validatePin(String pin) {
    try {
      if (Server.validatePin(this.card, pin)) { // Send PIN to the server and validate
        return true;
      } else {
        this.card = null;
        return false;
      }
    } catch (Exception e) {
      // Failed to validate the PIN
    }
    return false;
  }

  public boolean retrieveAccounts() throws Exception {
    try {
      this.accounts = Server.retrieveAccounts(this.card); // Retrieve account information from the server
    } catch (Exception e) {
      throw e;
    }
    return true;
  }
  
  public int getBalance(int i) {
    return this.accounts[i].balance;
  }
  
  public void deposit(int i, int num) {
    this.balance += num;
    this.accounts[i].balance += num;
    Server.updateBalance(this.accounts[i]); // Let the server know
  }
  
  public void withdraw(int i, int num) {
    if (this.balance >= num && this.accounts[i].balance >= num) {
      this.balance -= num;
      this.accounts[i].balance -= num;
      Server.updateBalance(this.accounts[i]); // Let the server know
    }
  }
  
  class Card {
    public Card() { }
  }
  
  class Account {
    int balance;
    
    public Account() { }
  }
}
