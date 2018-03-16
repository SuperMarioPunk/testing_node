'use strict';

const mochaPlugin = require('serverless-mocha-plugin');
const expect = mochaPlugin.chai.expect;

var padding = require('../lib/paddingSupport.js');

describe('Padding Support', () => {
    before((done) => {
        done();
    });
    describe('Padding start', () => {
        before((done) => {
            done();
        });
        it('null string', () => {
            expect(padding.padStart("", 10, '0')).to.equals("0000000000");
        });
        it('null mask', () => {
            expect(padding.padStart("1", 10, null)).to.equals("         1");
        });
        it('string.length > max ', () => {
            expect(padding.padStart("142507", 3, '0')).to.equals("142507");
        });
        it('valid string ', () => {
            expect(padding.padStart("125", 5, '0')).to.equals("00125");
        });
    });
    describe('Padding end', () => {
        before((done) => {
            done();
        });
        it('null string', () => {
            expect(padding.padEnd("", 10, ' ')).to.equals("          ");
        });
        it('null mask', () => {
            expect(padding.padEnd("1", 10, null)).to.equals("1         ");
        });
        it('string.length > max ', () => {
            expect(padding.padEnd("142507", 3, '0')).to.equals("142507");
        });
        it('valid string ', () => {
            expect(padding.padEnd("ARF", 5, ' ')).to.equals("ARF  ");
        });
    });
});
