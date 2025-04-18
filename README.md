# Stable Marriages (Gale-Shapley Algorithm)

This Java project implements the Gale-Shapley algorithm to solve the Stable Marriage Problem, applied here to match students with employers based on mutual ranked preferences.

## 📚 Description

Given `n` students and `n` employers, each with their own ranked preferences over the other group, the goal is to produce a **stable matching** where no pair (student, employer) would rather be with each other than their current match. This implementation guarantees stability using the **deferred acceptance** algorithm.

## 🧠 Features

- Reads input files defining preferences for students and employers
- Produces stable matchings using a greedy algorithm
- Outputs results to a formatted `.txt` file
- Handles multiple input sizes (3, 10, 50 participants)
- Uses custom data structures for practice with Java ADTs

## 🧱 Data Structures Used

- **`PQ`**: A custom priority queue built by extending `PriorityQueue<Couple>`, where `Couple` implements `Comparable`
- **`Sue`**: A custom stack that extends `Stack<Integer>`, used for managing proposals
- **`HashMap`**: For quick access to ranking data
- **`Couple`**: A key-value wrapper class used to prioritize proposals

## 📂 File Structure

- `GaleShapley.java` – Main logic for reading input, processing, and outputting matches
- `Couple.java` – Key-value pair class for use in the priority queue
- `PQ.java` – Custom priority queue implementation
- `Sue.java` – Custom stack class
- `test_N3.txt`, `test_N10.txt`, `test_N50.txt` – Sample input files
- `matches_test_N3.txt`, etc. – Output files generated after matching
