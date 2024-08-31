import streamlit as st
import sqlite3

conn=sqlite3.connect("pizzadb.sqlite3")
c=conn.cursor()

c.execute("CREATE TABLE IF NOT EXISTS pizzas(name TEXT,smPrice REAL,mdPrice REAL,lgPrice REAL,contents TEXT,image TEXT)")
conn.commit()



st.header("Add Pizza")


with st.form("addPizza",clear_on_submit=True):
    name=st.text_input("Pizza name")
    smPrice=st.number_input("Small Price")
    mdPrice = st.number_input("Medium Price")
    lgPrice = st.number_input("Large Price")

    contents=st.multiselect("Contents",["mushroom","ham","bacon","olives","mozzarella","green olives","chicken","tuna","pineapple","basil","sausage","salami","celery"])

    image=st.file_uploader("Please Add image of pizza")

    add=st.form_submit_button("Add pizza")

    if add:
        contents=str(contents)
        contents=contents.replace("[","")
        contents=contents.replace("]","")
        contents=contents.replace("'","")
        st.write(contents)

        if image is not None:
            imageUrl="img/"+image.name
            open(imageUrl,"wb").write(image.read())

            c.execute("INSERT INTO pizzas VALUES(?,?,?,?,?,?)",(name,smPrice,mdPrice,lgPrice,contents,imageUrl))
            conn.commit()

            st.success("Pizza Added Successfuly")
