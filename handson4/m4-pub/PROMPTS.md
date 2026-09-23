Session 4A:-
Part A :-
AI's response -
'''
Plain English explanation
This stack trace is not saying the program crashed. It is saying:

JUnit ran the test method named quotesDiscountForLoyalCustomer in PriceEngineTest.java.
That test expected a final price of 90.0 for a 5-year loyalty customer.
The actual result was 95.0.
So the code under test produced the wrong amount. In everyday terms:

“The system said a 5-year loyal customer only got a 5% discount, but the test says a 5-year customer should get 10%.”

The important part is this line:

expected: <90.0> but was: <95.0>
That means the application returned a value that was too high by 5.0, which matches a 5% discount instead of 10%.

Most likely root cause
The logic in PriceEngine.java defines the loyalty tiers like this:

1–2 years = 2%
3–4 years = 5%
5+ years = 10%
But the condition for the 10% tier is written as:

years > 5
instead of:

years >= 5
Because of that off-by-one bug, a customer with exactly 5 years falls through to the 5% tier instead of the 10% tier.

That is why the test for 5 years gets 95.0 instead of 90.0. This is the most likely root cause, and it matches the test description and the failure message exactly.
'''

This was given using 1 line of stack trace. 
I agree with the provided answer becaue in the code that itself is the root issue.

Part B :-
it was able to fix it in 1 go. 
changed the line if (years > 5) to if (years >= 5).

Part C  :-
In this case both of the replies were helpful, though the explanation was easy for a person to understand. 

Session 4B:-
I calculated cyclomatic complexity to be 22+1 = 23 (if, if else, for, ||, &&, throw) 
The same result is given by pmd.

Part A:-
AI response :-
'''
(a) Current Cyclomatic Complexity Estimate: 20 (21 including compound boolean conditions).

(b) Single Refactor Move: extract-method

(c) Expected Complexity Reduction: Extracting each calculation step (subtotal calculation, promo discount logic, loyalty tier determination, and tax rates) into dedicated helper methods will reduce quote's cyclomatic complexity from 20 to 1 by converting its body into a clean, linear sequence of method calls.
'''

Part D:-
