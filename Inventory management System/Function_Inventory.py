class Product:
    def __init__(self, product_id, name, category, price, quantity):
        self.product_id = product_id
        self.name = name
        self.category = category
        self.price = price
        self.quantity = quantity
    
    def display_info(self):
        return f"ID: {self.product_id}| Name: {self.name} | Category: {self.category}| Price: {self.price} BDT| Quantity: {self.quantity}"
    

class Category:
    def __init__(self, category_name):
        self.category_name = category_name
        self.products = []
    
    def add_product(self, product):
        self.products.append(product)
    def list_products(self):
        return [product.display_info() for product in self.products]
    
class Inventory:
    def __init__(self):
        self.categories = {}

    def add_category(self, category):
        if category.category_name not in self.categories:
            self.categories[category.category_name] = category
        else:
            print(f"Category '{category.category_name}' already exists.")

    def add_product_to_category(self, product):
        if product.category in self.categories:
            self.categories[product.category].add_product(product)
        else:
            new_category = Category(product.category)
            new_category.add_product(product)
            self.categories[product.category] = new_category

    def list_all_products(self):
        for category_name, category in self.categories.items():
            print(f"\nCategory: {category_name}")
            for info in category.list_products():
                print(info)

