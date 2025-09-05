# ETL Pipeline — Assignment 2
**Package:** `org.howard.edu.lsp.assignment2`  
**Main class:** `ETLPipeline`  
**Language:** Java 11+  
**Input:** `data/products.csv`  
**Output:** `data/transformed_products.csv`

## What it does
Implements an ETL (Extract–Transform–Load) pipeline over `products.csv`.

- **Extract:** Read CSV with columns: `ProductID,Name,Price,Category` (header present).
- **Transform (in order):**
  1) Uppercase `Name`  
  2) If `Category == Electronics`: apply **10% discount** to `Price` and **round to 2 decimals** (HALF_UP)  
  3) If **post-discount** `Price > 500.00` **and** original category was `Electronics`, set `Category = Premium Electronics`  
  4) Compute `PriceRange` from final `Price`:  
     - `0.00–10.00 → Low`  
     - `10.01–100.00 → Medium`  
     - `100.01–500.00 → High`  
     - `500.01+ → Premium`
- **Load:** Write to `data/transformed_products.csv` with header:  
  `ProductID,Name,Price,Category,PriceRange`

## Assumptions
- CSV is comma-delimited; fields contain **no commas or quotes**.
- The **first row is a header** and is not transformed.
- Invalid rows (wrong column count, bad numeric parsing) are **skipped** (not fatal).
- Rounding is **two decimals, HALF_UP** via `BigDecimal#setScale(2, RoundingMode.HALF_UP)`.
- Uses **relative paths** so the project runs the same on any machine:
  - Run from `src/` (`../data/...`) or project root with `-cp
 
## Project layout
JavaProjectRoot/
├─ data/
│ ├─ products.csv # input (you provide)
│ └─ transformed_products.csv # output (program creates)
└─ src/
└─ org/howard/edu/lsp/assignment2/
└─ ETLPipeline.java

  
## How to run

in terminal:
cd src
javac org/howard/edu/lsp/assignment2/ETLPipeline.java
java  org.howard.edu.lsp.assignment2.ETLPipeline


## AI Usage
ChatGPT
1) Used for debugging, proper file structure guidance, code production
2) Example prompt: "for this new project it goes JavaProjectRoot/src/org/howard/edu/lsp/assignment2/ETLPipeline.java (the file im working in)"
     - Needed guidance on file structure as I am new to Java rules
3) Response: "JavaProjectRoot/
 └── src/
      └── org/
           └── howard/
                └── edu/
                     └── lsp/
                          └── assignment2/
                               └── ETLPipeline.java"
4) How I used this: This was mainly just a double check. I used this as a baseline to structure my files.
  
   

## External Source Usage
Link: Google search
Usage: General java questions, csv manipulation questions
For example: "use java to manipulate a csv into a new csv"
