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
  Account account;

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
        return false;
      }
    } catch (Exception e) {
      // Failed to validate the PIN
    }
    return false;
  }

  public boolean RetrieveAccount(String accountNum) throws Exception {
    try {
      this.account = Server.retrieveAccount(accountNum); // Retrieve account information from the server
    } catch (Exception e) {
      throw e;
    }
    return true;
  }
  
  public Account selectAccount(String accountNum) throws Exception {
    if (RetrieveAccount(accountNum)) { // Retrieve account information from the server
      return this.account;
    }
    
    return null;
  }
  
  public int getBalance() {
    return this.account.balance;
  }
  
  public void deposit(int num) {
    this.balance += num;
    this.account.balance += num;
    Server.updateBalance(this.account); // Let the server know
  }
  
  public void withdraw(int num) {
    if (this.balance >= num && this.account.balance >= num) {
      this.balance -= num;
      this.account.balance -= num;
      Server.updateBalance(this.account); // Let the server know
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
