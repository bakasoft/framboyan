package org.bakasoft.framboyan.util;

import org.bakasoft.framboyan.Framboyan;
import org.bakasoft.framboyan.annotation.Describe;

@Describe("class Strings")
public class StringsTest extends Framboyan {{
    describe("method split(String, char)", () -> {
        it("should return empty array on null input", () -> {
            expect(Strings.split(null, ',')).not.toBeNull();
            expect(Strings.split(null, ',')).toBeEmpty();
        });
        it("should split the given string", () -> {
            expect(Strings.split("a-b-c", '-')).toBe("a", "b", "c");
            expect(Strings.split("a,b,c", ',')).toBe("a", "b", "c");
        });
    });
    describe("method lastPart(String, int)", () -> {
        it("should cut the string when is larger", () -> {
            expect(Strings.lastPart("abcdef", 1)).toBe("f");
            expect(Strings.lastPart("abcdef", 2)).toBe("ef");
            expect(Strings.lastPart("abcdef", 3)).toBe("def");
            expect(Strings.lastPart("abcdef", 4)).toBe("cdef");
            expect(Strings.lastPart("abcdef", 5)).toBe("bcdef");
        });
        it("should return the input when is shorter", () -> {
            expect(Strings.lastPart("abc", 3)).toBe("abc");
            expect(Strings.lastPart("abc", 4)).toBe("abc");
            expect(Strings.lastPart("abc", 6)).toBe("abc");
            expect(Strings.lastPart("abc", 7)).toBe("abc");
        });
    });
    describe("method repeat(char, int)", () -> {
        it("should repeat the char the specified number of times", () -> {
            expect(Strings.repeat('.', 0)).toBe("");
            expect(Strings.repeat('.', 1)).toBe(".");
            expect(Strings.repeat('.', 2)).toBe("..");
            expect(Strings.repeat('.', 3)).toBe("...");
        });
    });
    describe("method trimEnd(String)", () -> {
        it("should remove the last whitespaces", () -> {
            expect(Strings.trimEnd("")).toBe("");
            expect(Strings.trimEnd(" a ")).toBe(" a");
            expect(Strings.trimEnd("asdf \r\n \r\n\t\r\n")).toBe("asdf");
        });
    });
}}