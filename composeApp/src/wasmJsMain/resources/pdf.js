/**
* Create a new jsPDF instance with custom page size.
* @param {number} width - Page width in chosen unit (default: px)
* @param {number} height - Page height in chosen unit (default: px)
* @returns {jsPDF} - jsPDF instance
*/
function createPdf(width, height) {
    return new window.jspdf.jsPDF({
        unit: 'px',
        format: [width, height]
    });
}

function addImage(doc, dataUrl, x, y, width, height) {
    doc.addImage(dataUrl, "PNG", x, y, width, height,undefined, "FAST");
}

function addPage(doc) {
    doc.addPage();
}

function outputBlob(doc) {
    return doc.output("blob");
}