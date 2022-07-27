package activities;

public class BankAccount {

        private Integer balance;

        // Create a constructor
        public BankAccount(Integer initialBalance) {
            balance = initialBalance;
        }

        // Add method to calculate
        // balance amount after withdrawal
        public Integer withdraw(Integer amount) {
            if (balance < amount) {
                throw new NotEnoughFundsException(amount, balance);
            }
            balance -= amount;
            return balance;
        }

    public class NotEnoughFundsException extends RuntimeException {

        public NotEnoughFundsException(Integer amount, Integer balance) {
            super("Attempted to withdraw " + amount + " with a balance of " + balance);
        }

    }
}

