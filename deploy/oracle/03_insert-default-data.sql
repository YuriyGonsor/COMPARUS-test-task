whenever sqlerror exit failure rollback;

TRUNCATE TABLE john_doe.MY_USERS;

INSERT ALL
INTO john_doe.MY_USERS (USER_ID, USER_NAME, NAME, LASTNAME) VALUES ('iejrdfng',   'somebody', 'What am',          'I doing here')
INTO john_doe.MY_USERS (USER_ID, USER_NAME, NAME, LASTNAME) VALUES ('ksrjldnf',   'anybody',  'God',              'save America')
INTO john_doe.MY_USERS (USER_ID, USER_NAME, NAME, LASTNAME) VALUES ('elvfscdnm',  'nobody',   'Somebody that',    'I used to know')
INTO john_doe.MY_USERS (USER_ID, USER_NAME, NAME, LASTNAME) VALUES ('elgknwtemr', 'everybody','Du, du hast',      'Du hast mich')
SELECT 1 FROM dual;
