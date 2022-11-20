# Final Project README 
Author: Wesley Ryther, Gabriel Comstock, and Sean Arthur \
Program: Banking \
Description: Program can create 3 different bank account types from UI or JSON file, calculate the total balance after the specified period, and make withdrawals.

### Java Super Class
- Account

### Java Sub Classes
- Savings
- Checkings
  - Special Behavior - A 5 dollar fee is taken away from the balance each period.
- Certificate of Deposit
  - Special Behavior - User is prompted to enter the term, or when the CD will reach maturity

### BankerForm

#### Swing jPanel

- North panel: Title (Create Account)
- South panel: Jpanel
  - Clickable buttons to save/create accounts (Flow panel)
    - Button: Create - create account objects
    - Button: Show final balance - Should return each accounts final balance after computations have been done
- Center: Another jPanel
  - Inner north panel: jpanel
    - Fields to enter information into
      - Drop down field: Type of account
      - Beginning balance
      - Interest rate
      - Number of periods
      - Maturity
        - should only appear if account type is CD
  - Inner center panel: Description of accounts that will be created and current balance


### Banker(Main Method/jOptionPane)
- Main calls the following methods to execute the program
  - userPrompt
  - displayOutput

#### userPrompt Method
- Asks what kind of account to create
- Creates the chosen account
- Asks to fill in the attributes
- Adds it to an arrayList
  - used for displayOutput

#### displayOutput Method
- Iterates over the arrayList set up in userPrompt, or over each created bank account
- Run the compute method to calculate the final balance in the account
- Display the final balance for each account

## Diagram

![Vehicles Class Diagram](https://github.com/comstogm/IT2045CFinalProject/blob/master/IT2045CFinalProject.drawio.png)