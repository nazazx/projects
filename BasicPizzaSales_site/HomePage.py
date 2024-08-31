import streamlit as st
import  pandas as pd
import sqlite3

st.header("Home Page")

try:
    conn=sqlite3.connect("pizzadb.sqlite3")

    c=conn.cursor()

    c.execute("SELECT * FROM orders")

    contents=c.fetchall()

    df=pd.DataFrame(contents)


    df.columns=["name","Address","pizza","size","baverage","totalPrice"]

    st.table(df)

except Exception:
    st.write("There is no order now...")
