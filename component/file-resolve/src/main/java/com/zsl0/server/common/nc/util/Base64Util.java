package com.zsl0.server.common.nc.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName Base64Util
 * @Date 2020/9/6 19:21
 * @Author lqr
 * @VERSION 1.0
 **/
public class Base64Util {

    private static Map<Character,Integer> base;
    private static Map<Integer,Character> vbase;

    static {
        base=new ConcurrentHashMap<>(64);
        base.put('A',0);
        base.put('B',1);
        base.put('C',2);
        base.put('D',3);
        base.put('E',4);
        base.put('F',5);
        base.put('G',6);
        base.put('H',7);
        base.put('I',8);
        base.put('J',9);
        base.put('K',10);
        base.put('L',11);
        base.put('M',12);
        base.put('N',13);
        base.put('O',14);
        base.put('P',15);
        base.put('Q',16);
        base.put('R',17);
        base.put('S',18);
        base.put('T',19);
        base.put('U',20);
        base.put('V',21);
        base.put('W',22);
        base.put('X',23);
        base.put('Y',24);
        base.put('Z',25);
        base.put('a',26);
        base.put('b',27);
        base.put('c',28);
        base.put('d',29);
        base.put('e',30);
        base.put('f',31);
        base.put('g',32);
        base.put('h',33);
        base.put('i',34);
        base.put('j',35);
        base.put('k',36);
        base.put('l',37);
        base.put('m',38);
        base.put('n',39);
        base.put('o',40);
        base.put('p',41);
        base.put('q',42);
        base.put('r',43);
        base.put('s',44);
        base.put('t',45);
        base.put('u',46);
        base.put('v',47);
        base.put('w',48);
        base.put('x',49);
        base.put('y',50);
        base.put('z',51);
        base.put('0',52);
        base.put('1',53);
        base.put('2',54);
        base.put('3',55);
        base.put('4',56);
        base.put('5',57);
        base.put('6',58);
        base.put('7',59);
        base.put('8',60);
        base.put('9',61);
        base.put('+',62);
        base.put('/',63);
        base.put('=',64);

        vbase=new ConcurrentHashMap<>(64);
        base.forEach((k,v)->{
            vbase.put(v,k);
        });
    }

    /**
     * 根据字符获取索引信息
     * @param ch
     * @return
     */
    public static int getValueByChar(char ch){
        return base.get(ch);
    }

    /**
     * 根据值获取字符
     * @param value
     * @return
     */
    public static char getCharByValue(int value){
        return vbase.get(value);
    }


}
