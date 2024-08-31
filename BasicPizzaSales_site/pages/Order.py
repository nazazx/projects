import streamlit as st
import sqlite3
import pandas as pd

conn = sqlite3.connect("pizzadb.sqlite3")
c = conn.cursor()

c.execute(
    "CREATE TABLE IF NOT EXISTS orders(name TEXT, address TEXT, pizza TEXT, size TEXT, baverage TEXT, price REAL)")

c.execute("SELECT name FROM pizzas")
names = c.fetchall()

nameList = [i[0] for i in names]

st.header("Order")

with st.form("order", clear_on_submit=True):
    name = st.text_input("Name Surname")
    address = st.text_area("Address")
    pizza = st.selectbox("Select pizza", nameList)
    size = st.selectbox("Size", ["Small", "Medium", "Large"])
    baverage = st.selectbox("Baverage", ["Ayran", "Soda", "Cola", "Fanta", "Ice Tea"])

    giveOrder = st.form_submit_button("Give Order")

    if giveOrder:
        if size == "Small":
            c.execute("SELECT smPrice FROM pizzas WHERE name=?", (pizza,))
            price = c.fetchone()
        elif size == "Medium":
            c.execute("SELECT mdPrice FROM pizzas WHERE name=?", (pizza,))
            price = c.fetchone()
        elif size == "Large":
            c.execute("SELECT lgPrice FROM pizzas WHERE name=?", (pizza,))
            price = c.fetchone()

        baverages = {
            "Ayran": 15,
            "Soda": 15,
            "Cola": 20,
            "Fanta": 20,
            "Ice Tea": 20
        }
        baveragePrice = baverages[baverage]

        totalPrice = price[0] + baveragePrice

        st.write(totalPrice)

        c.execute("INSERT INTO orders VALUES(?,?,?,?,?,?)", (name, address, pizza, size, baverage, totalPrice))
        conn.commit()
        st.success(f"Order completed successfully! Total Price: {totalPrice} TL")


st.header("Delete Order")

with st.form("Delete Order"):
    c.execute("SELECT rowid, * FROM orders")
    orders = c.fetchall()


    if orders:
        df = pd.DataFrame(orders, columns=["index", "name", "address", "pizza", "size", "baverage", "totalPrice"])
        order_to_delete = st.selectbox("Select an order to delete",
                                       [f"{order[0]}: {order[1]} - {order[2]} - {order[3]}" for order in orders])
        deleteOrder = st.form_submit_button("Delete Order")
        if deleteOrder:
            order_id = int(order_to_delete.split(":")[0])
            c.execute("DELETE FROM orders WHERE rowid=?", (order_id,))
            conn.commit()
            st.success("Order deleted successfully!")

            st.experimental_set_query_params()
    else:
        st.write("No orders available to delete.")

conn.close()
