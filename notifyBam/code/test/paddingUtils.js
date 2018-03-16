'use strict';

const mochaPlugin = require('serverless-mocha-plugin');
const expect = mochaPlugin.chai.expect;

var paddingUtils = require('../lib/paddingUtils');

describe('Padding Utils', () => {
    describe('Padding numeric', () => {
        before((done) => {
            done();
        });
        it('undefined params', () => {
            expect(paddingUtils.numeric()).to.equal('0');
        });
        it('string undefined length 5', () => {
            expect(paddingUtils.numeric(undefined, 5)).to.equal('00000');
        });
        it('string null length 5', () => {
            expect(paddingUtils.numeric(null, 5)).to.equal('00000');
        });
        it('string \'1\' length undefined', () => {
            expect(paddingUtils.numeric('1', undefined)).to.equal('1');
        });
        it('string \'2\' length 5', () => {
            expect(paddingUtils.numeric('2', 5)).to.equal('00002');
        });
        it('expected length', () => {
            expect(paddingUtils.numeric('2', 5).length).to.equal(5);
        });
    });
    describe('Padding alpha', () => {
        before((done) => {
            done();
        });

        it('undefined params', () => {
            expect(paddingUtils.alpha()).to.equal(' ');
        });

        it('string undefined length 5', () => {
            expect(paddingUtils.alpha(undefined, 5)).to.equal('     ');
        });

        it('string null length 5', () => {
            expect(paddingUtils.alpha(null, 5)).to.equal('     ');
        });

        it('string \'a\' length null', () => {
            expect(paddingUtils.alpha('a', null)).to.equal('a');
        });

        it('string \'a\' length 5', () => {
            expect(paddingUtils.alpha('a', 5)).to.equal('a    ');
        });
        it('expected length', () => {
            expect(paddingUtils.alpha('a', 5).length).to.equal(5);
        });
    });
});  