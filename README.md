# Multithreaded factorial calculation

---

## Description

- One thread reads numbers from the file and passes them to the pool of threads.
- The pool of threads has a fixed quantity of threads that the user enters the console. 
- The pool calculates the factorial numbers and passes values to another thread that writes calculating data in the file.

---

## How to start?
1. Click `Run Main`.
2. Enter the number of threads in the pool to calculate factorial in the console.
3. Enjoy!
4. P.S. You can verify code and run `mvn clean package` in the terminal. Also, you can see logs in `logs/app.log`.
