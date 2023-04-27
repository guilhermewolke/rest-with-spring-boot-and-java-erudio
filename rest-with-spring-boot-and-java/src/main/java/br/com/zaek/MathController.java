package br.com.zaek;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.zaek.exceptions.UnsupportedMathOperationException;
import br.com.zaek.utils.Utils;

@RestController
public class MathController {
	
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value="/sum/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sum(@PathVariable(value="numberOne") String numberOne, @PathVariable(value="numberTwo") String numberTwo) throws Exception {
		if (!Utils.isNumeric(numberOne) || !Utils.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set two numeric values!");
		}
		return Utils.convertToDouble(numberOne) + Utils.convertToDouble(numberTwo);
	}
	
	@RequestMapping(value="/sub/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sub(@PathVariable(value="numberOne") String numberOne, @PathVariable(value="numberTwo") String numberTwo) throws Exception {
		if (!Utils.isNumeric(numberOne) || !Utils.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set two numeric values!");
		}
		return Utils.convertToDouble(numberOne) - Utils.convertToDouble(numberTwo);
	}
	
	@RequestMapping(value="/multiply/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double multiply(@PathVariable(value="numberOne") String numberOne, @PathVariable(value="numberTwo") String numberTwo) throws Exception {
		if (!Utils.isNumeric(numberOne) || !Utils.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set two numeric values!");
		}
		return Utils.convertToDouble(numberOne) * Utils.convertToDouble(numberTwo);
	}
	
	@RequestMapping(value="/divide/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double divide(@PathVariable(value="numberOne") String numberOne, @PathVariable(value="numberTwo") String numberTwo) throws Exception {
		if (!Utils.isNumeric(numberOne) || !Utils.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set two numeric values!");
		}
		
		if (numberOne.equals("0") || numberTwo.equals("0")) {
			throw new UnsupportedMathOperationException("Please set two numeric values different of 0");
		}
		return Utils.convertToDouble(numberOne) / Utils.convertToDouble(numberTwo);
	}
	
	@RequestMapping(value="/mid/{numberOne}/{numberTwo}/{numberThree}", method=RequestMethod.GET)
	public Double mid(
			@PathVariable(value="numberOne") String numberOne,
			@PathVariable(value="numberTwo") String numberTwo,
			@PathVariable(value="numberThree") String numberThree
			) throws Exception {
		if (!Utils.isNumeric(numberOne) || !Utils.isNumeric(numberTwo) || !Utils.isNumeric(numberThree)) {
			throw new UnsupportedMathOperationException("Please set two numeric values!");
		}
		
		if (numberOne.equals("0") && numberTwo.equals("0") && numberThree.equals("0")) {
			return 0D;
		}
		
		return (Utils.convertToDouble(numberOne) + Utils.convertToDouble(numberTwo) + Utils.convertToDouble(numberThree)) / 3;
	}
	
	@RequestMapping(value="/square/{number}", method=RequestMethod.GET)
	public Double square(@PathVariable(value="number") String number) throws Exception {
		if (!Utils.isNumeric(number)) {
			throw new UnsupportedMathOperationException("Please set a numeric values!");
		}
		
		if (number.equals("0")) {
			return 0D;
		}
		
		return Math.sqrt(Utils.convertToDouble(number));
	}
	
	
}
