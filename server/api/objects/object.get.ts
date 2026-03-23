import type {Object} from '@/types/object'

export default defineEventHandler((event): Object[] => {
    const query = getQuery(event)
    const search = (query.search as string)?.toLowerCase() || ''
    console.log('Search query:', search)
    const objects: Object[]= [
        {
            id: 1,
            name: 'Marteau',
            image: 'https://www.bordet.fr/Files/131539/Img/10/z590S1x1200.jpg',
            description: 'Ceci n\'est pas un marteau',
        },
        {
            id: 2,
            name: 'Perceuse',
            image: 'https://www.bosch-professional.com/fr/fr/ocsmedia/273743-54/application-image/1434x828/perceuse-a-percussion-pro-gsb-24-2-060119c801.png',
            description: 'Ceci n\'est pas une perceuse',
        },{
            id: 3,
            name: 'Tondeuse',
            image: 'https://media.castorama.fr/is/image/KingfisherDigital/5059340124445_02i?$WCMS_NPI_FW_S$',
            description: 'Ceci n\'est pas une tondeuse',
        },{
            id: 4,
            name: 'Livre',
            image: 'https://letandemlocation.com/cdn/shop/products/location-livre-ancien-vintage-mariage-lyon-1.jpg?v=1759913884',
            description: 'Ceci n\'est pas un livre',
        },
    ]
    if (!search) {
        return objects
    }

    return objects.filter(object =>
        object.name.toLowerCase().includes(search) ||
        object.description.toLowerCase().includes(search)
    )
})