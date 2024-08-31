import streamlit as st
import sqlite3


st.header("Catalog")



conn=sqlite3.connect("pizzadb.sqlite3")

c=conn.cursor()

c.execute("SELECT * FROM pizzas")

pizzas=c.fetchall()




for pizza in pizzas:
    col1,col2,col3=st.columns(3)
    with col1:
        st.image(pizza[5])
    with col2:
        st.subheader(pizza[0])
        st.write(pizza[4])
    with col3:
        st.write("Small",pizza[1],"$")
        st.write("Medium", pizza[2], "$")
        st.write("Large", pizza[3], "$")