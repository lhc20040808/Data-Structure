package com.lhc.datastructure.solution;

import com.lhc.datastructure.linearstructure.ArrayStack;

import java.util.Stack;

public class StackSolution {

    public boolean isValid(String s) {
        ArrayStack<Character> stack = new ArrayStack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[' || c == '{' || c == '(') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                char topChar = stack.pop();
                if (c == ')' && topChar != '(') {
                    return false;
                }
                if (c == ']' && topChar != '[') {
                    return false;
                }
                if (c == '}' && topChar != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        StackSolution solution = new StackSolution();
//        System.out.println(solution.isValid("(()}[]"));
//        System.out.println(solution.isValid("([{}])"));
        System.out.println(solution.isValid("(){}[]"));
    }
}
