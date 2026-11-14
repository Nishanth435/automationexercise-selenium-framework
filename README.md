# automationexercise-selenium-framework

The end-to-end UI automation flow for automationexercise.com is in Asessment.java, with test data read through Readexcel.java.

Site:	https://automationexercise.com/
## Test Flow
1.	Open	the	website
2.	Create	an	account
3.	Login	to	the	account
4.	Get	all	product	names
5.	Select	3	random	products
6.	Go	to	Cart
7.	Proceed	to	Checkout
8.	Fill	address	details	(one-time	only)
9.	Place	Order
10.	Enter	payment/card	details -Fill	the	payment	form	using	the	values	from	Excel
Click	Pay	and	Confirm	Order
11.	Download	the	invoice	PDF
12.	Logout
13.	File	Upload	Validation (Site:	https://the-internet.herokuapp.com/upload)


## Test Data
`CardDetails.xlsx` holds dummy card details (NameOnCard, CardNumber, CVC, ExpiryMonth, ExpiryYear).
These are test values only.


