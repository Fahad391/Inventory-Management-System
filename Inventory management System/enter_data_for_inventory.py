import Function_Inventory
from Function_Inventory import *

product1 = Product(product_id=1, name="Laptop", category="Electronics", price=41200, quantity=10)
product2 = Product(product_id=2, name="Headphones", category="Electronics", price=6150, quantity=50)
product3 = Product(product_id=3, name="Jeans", category="Clothing", price=1040, quantity=100)

electronics_category = Category("Electronics")
clothing_category = Category("Clothing")

inventory = Inventory()

inventory.add_category(electronics_category)
inventory.add_category(clothing_category)

inventory.add_product_to_category(product1)
inventory.add_product_to_category(product2)
inventory.add_product_to_category(product3)

print("Electronics Category Products:")
for info in electronics_category.list_products():
    print(info)

print("\nClothing Category Products:")
for info in clothing_category.list_products():
    print(info)
