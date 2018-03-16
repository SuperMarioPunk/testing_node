module.exports.padStart = function padStart(text, max, mask) {
    const cur = text.length;
    if (max <= cur) {
      return text;
    }
    const masked = max - cur;
    let filler = mask != null || mask != undefined ? String(mask) : ' ';
    while (filler.length < masked) {
      filler += filler;
    }
    const fillerSlice = filler.slice(0, masked);
    return fillerSlice + text;
  }

module.exports.padEnd = function padEnd(text, max, mask) {
    const cur = text.length;
    if (max <= cur) {
      return text;
    }
    const masked = max - cur;
    let filler = mask != null || mask != undefined ? String(mask) : ' ';
    while (filler.length < masked) {
      filler += filler;
    }
    const fillerSlice = filler.slice(0, masked);
    return text + fillerSlice;
  }
