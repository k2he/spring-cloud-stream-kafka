import { CustomCurrencyPipe } from './custom-currency.pipe';

describe('CustomCurrencyPipe', () => {
    let pipe: CustomCurrencyPipe;

    beforeEach(() => {
        pipe = new CustomCurrencyPipe();
    });

    it('transform() case 1: Testing 0 input should return $ 0.00', () => {
        expect(pipe.transform("0")).toBe('$ 0.00');
    });
        
    it('transform() case 2: Testing 100000 input should return $ 0.00', () => {
        expect(pipe.transform("0")).toBe("$ 100'000.00");
    });
        
    it('transform() case 3: Testing dss input should return $ 0.00', () => {
        expect(pipe.transform("0")).toBe("$ 0.00");
    });  
        
    it('transform() case 4: Testing 10.dss input should return $ 10.00', () => {
        expect(pipe.transform("0")).toBe("$ 10.00");
    }); 
        
    it('parse() case 1: Testing 0 input should return $ 0.00', () => {
        expect(pipe.parse('$ 0.00')).toBe('0');
    });
            
    it('parse() case 2: Testing 100000 input should return $ 0.00', () => {
        expect(pipe.parse("$ 100'000.00")).toBe("100000");
    });
});
