grammar ArrayFilterMap;

@parser::members
{
    public java.util.HashMap<String, Double> memory = new java.util.HashMap<String, Double>();
    @Override
    public void notifyErrorListeners(Token offendingToken, String msg, RecognitionException ex) {
        throw new RuntimeException(msg);
    }
}

@lexer::members
{
    @Override
    public void recover(RecognitionException ex) {
        throw new RuntimeException(ex.getMessage());
    }
}

callChain
    : calls += call ('%>%' calls += call)* EOF
    ;

call
    : mapCall
    | filterCall
    ;

filterCall
    : 'filter{' expression '}'
    ;

mapCall
    : 'map{' expression '}'
    ;

expression
    : element
    | constantExpression
    | binaryExpression
    ;

element
    : ELEMENT
    ;

binaryExpression
    : '(' expression operation expression ')'
    ;

constantExpression
    : '-' number
    | number
    ;

number
    : DIGIT+
    ;

operation
    : '+' | '-' | '*' | '>' | '<' | '=' | '&' | '|'
    ;

ELEMENT
    : 'element'
    ;

DIGIT
    : [0-9]
    ;

ErrorCharacter
    : .
    ;
