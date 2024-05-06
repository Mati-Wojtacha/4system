class CustomError extends Error {
    constructor(message, additionalData) {
        super(message);
        this.additionalData = additionalData;
    }
}
export default CustomError;