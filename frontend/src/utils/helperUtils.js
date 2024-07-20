/**
 * Only works with timestamp in milliseconds since the Unix Epoch (January 1, 1970).
 * @param timestamp i.e. '1704287581437'
 * @returns {{date: string, time: string}}
 */
export const formatJavaDate = (timestamp) => {
    const date = new Date(timestamp);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    const formattedDate = `${day}-${month}-${year}`;
    const formattedTime = `${hours}:${minutes}:${seconds}`;

    return {date: formattedDate, time: formattedTime};
}

/**
 *
 * @param timestamp
 * @returns {string}
 */
export const formatJavaDateToTimeAgo = (timestamp) => {
    const now = new Date();
    const date = new Date(timestamp);

    const seconds = Math.floor((now - date) / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    const months = Math.floor(days / 30);
    const years = Math.floor(days / 365);

    if (years > 0) {
        return `${years} y${years > 1 ? 'ears' : ''} ago`;
    } else if (months > 0) {
        return `${months} m${months > 1 ? 'onths' : ''} ago`;
    } else if (days > 0) {
        return `${days} d${days > 1 ? 'ays' : ''} ago`;
    } else if (hours > 0) {
        return `${hours} h${hours > 1 ? 'ours' : ''} ago`;
    } else if (minutes > 0) {
        return `${minutes} min${minutes > 1 ? 's' : ''} ago`;
    } else {
        return `${seconds} sec${seconds > 1 ? 's' : ''} ago`;
    }
};

/**
 *
 * @returns {Promise<*>}
 */
export const fetchCountries = async () => {
    try {
        const response = await fetch('https://countriesnow.space/api/v0.1/countries');

        if (response.ok) {
            const data = await response.json();

            return data.data.map(country => ({
                name: country.country,
                code: country.iso2,
                city: country.cities.map(city => city),
            }));
        }
    } catch (error) {
        console.error('Error fetching countries', error);
    }
}
