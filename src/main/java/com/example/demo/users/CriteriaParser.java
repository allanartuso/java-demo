package com.example.demo.users;

import com.google.common.base.Joiner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaParser {

    private static final Map<String, Operator> ops;

    private static final Pattern SpecCriteraRegex = Pattern.compile("^(\\w+?)(" + Joiner.on("|")
                                                                                        .join(SearchOperation.SIMPLE_OPERATION_SET) + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?)$");

    private enum Operator {
        OR(1), AND(2);
        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    static {
        Map<String, Operator> tempMap = new HashMap<>();
        tempMap.put("AND", Operator.AND);
        tempMap.put("OR", Operator.OR);
        tempMap.put("or", Operator.OR);
        tempMap.put("and", Operator.AND);

        ops = Collections.unmodifiableMap(tempMap);
    }

    private static boolean isHigerPrecedenceOperator(String currOp, String prevOp) {
        return (ops.containsKey(prevOp) && ops.get(prevOp).precedence >= ops.get(currOp).precedence);
    }

    public List<Object> parse(String searchParam) {

        List<Object> output = new LinkedList<>();
        Deque<String> stack = new LinkedList<>();

        Arrays.stream(searchParam.split("\\s+")).forEach(token -> {
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && isHigerPrecedenceOperator(token, stack.peek()))
                    output.add(stack.pop()
                                     .equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR :
                            SearchOperation.AND_OPERATOR);
                stack.push(token
                        .equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR :
                        SearchOperation.AND_OPERATOR);
            } else if (token.equals(SearchOperation.LEFT_PARANTHESIS)) {
                stack.push(SearchOperation.LEFT_PARANTHESIS);
            } else if (token.equals(SearchOperation.RIGHT_PARANTHESIS)) {
                while (!stack.peek()
                             .equals(SearchOperation.LEFT_PARANTHESIS))
                    output.add(stack.pop());
                stack.pop();
            } else {
                System.out.println(token);
                Matcher matcher = SpecCriteraRegex.matcher(token);
                while (matcher.find()) {
                    System.out.println(matcher.group(1));
                    System.out.println(matcher.group(2));
                    System.out.println(matcher.group(3));
                    System.out.println(matcher.group(4));
                    System.out.println(matcher.group(5));

                    output.add(new SpecSearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3), matcher
                            .group(4), matcher.group(5)));
                }
            }
        });

        while (!stack.isEmpty())
            output.add(stack.pop());

        Collections.reverse(output);
        return output;
    }
}